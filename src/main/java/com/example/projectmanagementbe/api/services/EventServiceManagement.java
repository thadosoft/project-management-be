package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.mappers.EventMapper;
import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.Event;
import com.example.projectmanagementbe.api.models.EventType;
import com.example.projectmanagementbe.api.models.LoanStatus;
import com.example.projectmanagementbe.api.models.dto.requests.CreateEventRequest;
import com.example.projectmanagementbe.api.models.dto.requests.EventRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateEventRequest;
import com.example.projectmanagementbe.api.models.dto.responses.EventResponse;
import com.example.projectmanagementbe.api.models.project.Project;
import com.example.projectmanagementbe.api.repositories.EventRepository;
import com.example.projectmanagementbe.api.repositories.project.ProjectRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceManagement implements EventService{

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ProjectRepository projectRepository;

    @Override
    public Page<EventResponse> findByParams(EventRequest request, Pageable pageable) {
        String title = request.getTitle();
        String typeStr = request.getType();
        String date = request.getStartDate();
        Integer month = request.getMonth();
        Integer quarter = request.getQuarter();
        Integer year = request.getYear();
        Long participantIds = request.getParticipantIds(); // mới


        EventType type = null;
        if (typeStr != null && !typeStr.isBlank()) {
            try {
                type = EventType.valueOf(typeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid event type");
            }
        }

        // Nếu không có filter nào -> lấy tất cả
        boolean noFilter = (title == null || title.isBlank())
                && (type == null)
                && (date == null || date.isBlank())
                && (month == null)
                && (quarter == null)
                && (year == null)
                && (participantIds == null); // thêm participantIds

        Page<Event> result;

        if (noFilter) {
            result = eventRepository.findAll(pageable);
        } else {
            result = eventRepository.findByParams(title, type, date, month, quarter, year, participantIds, pageable);
        }

        return result.map(eventMapper::mapEventResponse);
    }

    @Override
    public List<EventResponse> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::mapEventResponse)
                .toList();
    }

    @Override
    @Transactional
    public Long create(CreateEventRequest request) {
        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        }

        // 🔹 Tạo mới event
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setType(request.getType());
        event.setProject(project);

        eventRepository.save(event);
        return event.getId();
    }


    @Override
    public EventResponse findById(Long id) {
        return eventRepository.findById(id)
                .map(eventMapper::mapEventResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.EVENT_NOT_FOUND.getMessage())
                );
    }

    @Override
    @Transactional
    public void update(Long id, UpdateEventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.EVENT_NOT_FOUND.getMessage())
                );

        // Map những field cơ bản (String, LocalDateTime, Enum)
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setType(request.getType());

        // Cập nhật project nếu projectId có trong request
        if (request.getProjectId() != null && !request.getProjectId().isBlank()) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
            event.setProject(project);
        }

        eventRepository.save(event);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.EVENT_NOT_FOUND.getMessage())
                );

        eventRepository.delete(event);
    }
}
