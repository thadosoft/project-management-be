package com.example.projectmanagementbe.api.services.impls.referenceProfile;

import com.example.projectmanagementbe.api.mappers.referenceProfile.ReferenceLinkMapper;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceLinkRequest;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceLink;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceLinkRepository;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceProfileRepository;
import com.example.projectmanagementbe.api.services.referenceProfile.IProfileReferenceLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReferenceLinkService implements IProfileReferenceLink {

  private final ReferenceLinkRepository referenceLinkRepository;
  private final ReferenceProfileRepository referenceProfileRepository;

  public void createReferenceLink(ReferenceLinkRequest request) {

    System.out.println(request.getReferenceProfile());

    ReferenceProfile referenceProfile = referenceProfileRepository.findById(request.getReferenceProfile())
        .orElseThrow(() -> new RuntimeException("ReferenceProfile không tồn tại"));

    ReferenceLink referenceLink = new ReferenceLink();
    referenceLink.setReferenceProfile(referenceProfile); // Gán entity đã có trong database
    referenceLink.setLink(request.getLink());
    referenceLink.setDescription(request.getDescription());

    referenceLinkRepository.save(referenceLink);
  }
}
