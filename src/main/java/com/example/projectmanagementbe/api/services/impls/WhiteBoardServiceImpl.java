package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.mappers.WhiteBoardMapper;
import com.example.projectmanagementbe.api.models.WhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.CreateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.SearchWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.UpdateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.responses.WhiteBoardResponse;
import com.example.projectmanagementbe.api.repositories.WhiteBoardRepository;
import com.example.projectmanagementbe.api.services.WhiteBoardService;
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
public class WhiteBoardServiceImpl implements WhiteBoardService {

    private final WhiteBoardRepository whiteBoardRepository;
    private final WhiteBoardMapper whiteBoardMapper;    
    
    @Override
    public Page<WhiteBoardResponse> searchByParams(SearchWhiteBoard request, Pageable pageable) {
        LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
        LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
        return whiteBoardRepository
                .findByParams(startDate, endDate, pageable).map(whiteBoardMapper::mapWhiteBoardResponse);
    }

    @Override
    public void create(CreateWhiteBoard request) {
        WhiteBoard quotationRequest = whiteBoardMapper.map(request);
        whiteBoardRepository.save(quotationRequest);
    }

    @Override
    public void update(Long id, UpdateWhiteBoard request) {
        WhiteBoard quotationRequest = whiteBoardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorCode.WHITE_BOARD_NOT_FOUND.toString()));

        whiteBoardMapper.update(request, quotationRequest);

        whiteBoardRepository.save(quotationRequest);
    }

    @Override
    public void delete(Long id) {
        try {
            whiteBoardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.WHITE_BOARD_NOT_FOUND.toString());
        }
    }

    @Override
    public WhiteBoardResponse findById(Long id) {
        return whiteBoardMapper.mapWhiteBoardResponse(whiteBoardRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.WHITE_BOARD_NOT_FOUND)));
    }
}
