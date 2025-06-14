    package com.example.projectmanagementbe.api.services.impls.attandance;

    import com.example.projectmanagementbe.api.mappers.CaptureDatumMapper;
    import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.AttendanceRequest;
    import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
    import com.example.projectmanagementbe.api.models.dto.responses.dashboard.LateStaff;
    import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
    import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.GroupedAttendanceResponse;
    import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
    import com.example.projectmanagementbe.api.repositories.attandance.CaptureDatumRepository;
    import com.example.projectmanagementbe.api.services.attandance.ICaptureDatumService;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatterBuilder;
    import java.time.LocalTime;
    import java.time.temporal.ChronoField;
    import java.util.Comparator;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;
    import java.util.stream.Collectors;

    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageImpl;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import java.time.format.DateTimeFormatter;


    import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime2;

    @Service
    @RequiredArgsConstructor
    public class CaptureDatumServiceImpl implements ICaptureDatumService {

        private final CaptureDatumRepository captureDatumRepository;
        private final CaptureDatumMapper mapper;

        @Override
        public Page<CaptureDatumResponse> searchByParams(SearchCaptureDatumRequest request, Pageable pageable) {
            LocalDateTime startDate = parseDateToLocalDateTime2(request.getStartDate(), false);
            LocalDateTime endDate = parseDateToLocalDateTime2(request.getEndDate(), true);
            return captureDatumRepository.findByParams(request.getPersonName(), startDate, endDate, pageable).map(mapper::map);
        }

        @Override
        @Transactional
        public BigDecimal getAttendance(AttendanceRequest request) {

            Object[] results = captureDatumRepository.getAttendanceData(request.getDate(), request.getEmpCode());

            return (BigDecimal) results[0];
        }

        public List<LateStaff> getLatest6CaptureData() {
            List<CaptureDatum> captureDatumList = captureDatumRepository.findTop4ByOrderByCreatedAtDesc();
            return mapper.map(captureDatumList);
        }

        public Page<GroupedAttendanceResponse> searchGroupedAttendances(SearchCaptureDatumRequest request, Pageable pageable) {

            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd HH:mm:ss")
                    .optionalStart()
                    .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true)
                    .optionalEnd()
                    .toFormatter();
            LocalDateTime startDate = parseDateToLocalDateTime2(request.getStartDate(), false);
            LocalDateTime endDate = parseDateToLocalDateTime2(request.getEndDate(), true);


            List<CaptureDatum> rawData = captureDatumRepository.findAllByParams(request.getPersonName(), startDate, endDate);

            Map<String, List<CaptureDatum>> grouped = rawData.stream()
                    .collect(Collectors.groupingBy(d -> {
                        LocalDateTime parsedTime = LocalDateTime.parse(d.getTime(), formatter);
                        return d.getPersonId() + "_" + parsedTime.toLocalDate();
                    }));

            List<GroupedAttendanceResponse> groupedResponses = grouped.entrySet().stream().map(entry -> {
                        List<CaptureDatum> list = entry.getValue();
                        CaptureDatum first = list.get(0);

                        Optional<LocalDateTime> checkIn = list.stream()
                                .map(d -> LocalDateTime.parse(d.getTime(), formatter))
                                .filter(t -> t.toLocalTime().isBefore(LocalTime.NOON))
                                .min(LocalDateTime::compareTo);

                        Optional<LocalDateTime> checkOut = list.stream()
                                .map(d -> LocalDateTime.parse(d.getTime(), formatter))
                                .filter(t -> t.toLocalTime().isAfter(LocalTime.NOON))
                                .max(LocalDateTime::compareTo);

                        LocalDateTime firstDateTime = LocalDateTime.parse(first.getTime(), formatter);
                        String date = firstDateTime.toLocalDate().toString();

                        return new GroupedAttendanceResponse(
                                first.getPersonId(),
                                first.getPersonName(),
                                date,
                                checkIn.orElse(null),
                                checkOut.orElse(null)
                        );
                    }).sorted(Comparator.comparing(GroupedAttendanceResponse::getDate).reversed())
                    .toList();

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), groupedResponses.size());

            List<GroupedAttendanceResponse> paged = groupedResponses.subList(start, end);
            return new PageImpl<>(paged, pageable, groupedResponses.size());
        }
    }
