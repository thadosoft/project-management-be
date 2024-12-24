package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.creates.RoleCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.RoleUpdateRequest;
import com.example.projectmanagementbe.dto.responses.RoleResponse;
import com.example.projectmanagementbe.entities.RoleEntity;
import com.example.projectmanagementbe.mappers.RoleMapper;
import com.example.projectmanagementbe.repositories.RoleRepository;
import com.example.projectmanagementbe.services.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Override
  public List<RoleResponse> findAll() {
    return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
  }

  @Override
  public void create(RoleCreateRequest roleCreateRequest) {
    roleRepository.save(roleMapper.toRoleEntity(roleCreateRequest));
  }

  @Override
  public void update(Long id, RoleUpdateRequest roleUpdateRequest) {
    RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

    roleMapper.toRoleEntity(roleUpdateRequest, roleEntity);

    roleRepository.save(roleEntity);
  }

  @Override
  public void delete(Long id) {
    if (roleRepository.existsById(id)) {
      roleRepository.deleteById(id);
    } else {
      throw new RuntimeException("Role not found");
    }
  }

  @Override
  public RoleResponse findById(Long id) {
    return roleMapper.toRoleResponse(roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found")));
  }
}
