package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.EmployeeOfMonthMapper;
import com.example.projectmanagementbe.api.models.EmployeeOfMonth;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.CreateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.SearchEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.UpdateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.responses.employeeOfMonth.EmployeeOfMonthResponse;
import com.example.projectmanagementbe.api.repositories.EmployeeOfMonthRepository;
import com.example.projectmanagementbe.api.services.IEmployeeOfMonthService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
public class EmployeeOfMonthServiceImpl implements IEmployeeOfMonthService {

    private final EmployeeOfMonthRepository employeeOfMonthRepository;
    private final EmployeeOfMonthMapper employeeOfMonthMapper;

    @Override
    public Page<EmployeeOfMonthResponse> findByParams(SearchEmployeeOfMonthRequest request, Pageable pageable) {
        LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
        LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
        return employeeOfMonthRepository
                .findByParams(request.getName(), startDate, endDate, pageable).map(employeeOfMonthMapper::mapEmployeeOfMonthResponse);
    }


    @Override
    public void create(CreateEmployeeOfMonthRequest request) {
        EmployeeOfMonth quotationRequest = employeeOfMonthMapper.mapEmployeeOfMonth(request);
        employeeOfMonthRepository.save(quotationRequest);
    }

    @Override
    public void update(Long id, UpdateEmployeeOfMonthRequest request) {
        EmployeeOfMonth employee = employeeOfMonthRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorCode.EMPLOYEE_OF_MONTH_NOT_FOUND.toString()));

        employeeOfMonthMapper.update(request, employee);

        employeeOfMonthRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        try {
            employeeOfMonthRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.EMPLOYEE_OF_MONTH_NOT_FOUND.toString());
        }
    }

    @Override
    public EmployeeOfMonthResponse findById(Long id) {
        return employeeOfMonthMapper.mapEmployeeOfMonthResponse(employeeOfMonthRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_OF_MONTH_NOT_FOUND)));
    }
}
