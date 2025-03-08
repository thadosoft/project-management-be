package com.example.projectmanagementbe.api.services.impls.quotation;

import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;

import com.example.projectmanagementbe.api.mappers.quotation.QuotationMapper;
import com.example.projectmanagementbe.api.models.MaterialQuotation;
import com.example.projectmanagementbe.api.models.QuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.CreateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.SearchQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.UpdateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.responses.quotation.QuotationResponse;
import com.example.projectmanagementbe.api.repositories.quotation.QuotationRequestRepository;
import com.example.projectmanagementbe.api.services.quotation.IQuotationRequestService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QuotationRequestServiceImpl implements IQuotationRequestService {

  private final QuotationMapper quotationMapper;
  private final QuotationRequestRepository quotationRequestRepository;

  @Override
  public Page<QuotationResponse> searchByParams(SearchQuotationRequest request, Pageable pageable) {
    LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
    LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
    return quotationRequestRepository
        .findByParams(request.getRequesterName(), request.getReceiverName(), startDate, endDate, pageable).map(quotationMapper::mapQuotationResponse);
  }

  @Override
  public void create(CreateQuotationRequest request) {
    QuotationRequest quotationRequest = quotationMapper.map(request);

    quotationRequest.getMaterialQuotations().forEach(material -> material.setQuotationRequest(quotationRequest));

    quotationRequestRepository.save(quotationRequest);
  }

  @Override
  @Transactional
  public void update(Long id, UpdateQuotationRequest request) {
    QuotationRequest quotationRequest = quotationRequestRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            ErrorCode.QUOTATION_NOT_FOUND.toString()));

    quotationMapper.update(request, quotationRequest);

    quotationRequest.getMaterialQuotations().forEach(material -> material.setQuotationRequest(quotationRequest));

    quotationRequestRepository.save(quotationRequest);
  }

  @Override
  public void delete(Long id) {
    try {
      quotationRequestRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.QUOTATION_NOT_FOUND.toString());
    }
  }

  @Override
  public QuotationResponse findById(Long id) {
    return quotationMapper.mapQuotationResponse(quotationRequestRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.QUOTATION_NOT_FOUND)));
  }

  @Override
  public Map<String, Object> loadData(Long id) {
    Optional<QuotationRequest> request = quotationRequestRepository.findById(id);

    if (request.isPresent()) {
      QuotationRequest quotationRequest = request.get();
      var parameter = new HashMap<String, Object>();

      parameter.put("requesterName", quotationRequest.getRequesterName());
      parameter.put("requesterEmail", quotationRequest.getRequesterEmail());
      parameter.put("requesterTel", quotationRequest.getRequesterTel());
      parameter.put("requesterAddress", quotationRequest.getRequesterAddress());
      parameter.put("requesterWebsite", quotationRequest.getRequesterWebsite());
      parameter.put("receiverName", quotationRequest.getReceiverName());
      parameter.put("receiverEmail", quotationRequest.getReceiverEmail());
      parameter.put("receiverTel", quotationRequest.getReceiverTel());
      parameter.put("receiverAddress", quotationRequest.getReceiverAddress());
      parameter.put("receiverWebsite", quotationRequest.getReceiverWebsite());

      JRBeanCollectionDataSource materialQuotationDataSource =
          new JRBeanCollectionDataSource(quotationRequest.getMaterialQuotations());

      parameter.put("p_materialQuotationList", materialQuotationDataSource);

      return parameter;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.CONTRACT_NOT_FOUND.toString());
    }
  }

}
