package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.LeaveRequestMapper;
import com.example.projectmanagementbe.api.models.LeaveRequests;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.SearchLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import com.example.projectmanagementbe.api.repositories.attandance.LeaveRequestsRepository;
import com.example.projectmanagementbe.api.services.LeaveRequestsService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class LeaveRequestsServiceImpl implements LeaveRequestsService {

    private final LeaveRequestMapper leaveRequestMapper;
    private final LeaveRequestsRepository leaveRequestsRepository;

    @Override
    public Page<LeaveResponse> searchByParams(SearchLeaveRequest request, Pageable pageable) {
        LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
        LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
        return leaveRequestsRepository
                .findByParams(startDate, endDate, pageable).map(leaveRequestMapper::mapLeaveResponse);
    }

    @Override
    public void create(CreateLeaveRequest request) {
        LeaveRequests quotationRequest = leaveRequestMapper.map(request);
        leaveRequestsRepository.save(quotationRequest);
    }

    @Override
    public void update(Long id, UpdateLeaveRequest request) {
        LeaveRequests quotationRequest = leaveRequestsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorCode.LEAVE_REQUEST_NOT_FOUND.toString()));

        leaveRequestMapper.update(request, quotationRequest);

        leaveRequestsRepository.save(quotationRequest);
    }

    @Override
    public void delete(Long id) {
        try {
            leaveRequestsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.LEAVE_REQUEST_NOT_FOUND.toString());
        }
    }

    @Override
    public LeaveResponse findById(Long id) {
        return leaveRequestMapper.mapLeaveResponse(leaveRequestsRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.LEAVE_REQUEST_NOT_FOUND)));
    }
}
