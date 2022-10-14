package com.dailycodebuffer.user.service;

import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.repository.UserRepository;
import com.dailycodebuffer.user.valueobject.Department;
import com.dailycodebuffer.user.valueobject.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RestTemplate restTemplate;

  public User saveUser(User user) {
    log.info("inside saveUser of UserService");
    return userRepository.save(user);
  }

  public ResponseTemplateVO getUserWithDepartment(Long userId) {
    log.info("inside getUserWithDepartment of UserService");
    ResponseTemplateVO vo = new ResponseTemplateVO();
    User user = userRepository.findByUserId(userId);

    // Rest Template
    // rest call to the department-service using restTemplate
    Department department =  restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(), Department.class);

    vo.setUser(user);
    vo.setDepartment(department);
    return vo;
  }
}
