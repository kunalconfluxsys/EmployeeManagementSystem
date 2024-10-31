package com.example.employee_sytem.dto;


import lombok.*;


/**
 * Data Transfer Object for Employee.
 * <p>
 * This class represents the employee data to be transferred between
 * the client and the server, encapsulating the employee's attributes.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
 public class EmployeeDto {
 /**
  * The unique identifier for the employee.
  */
  private Long id;
 /**
  * The first name of the employee.
  */
  private String firstName;
 /**
  * The last name of the employee.
  */
  private String lastName;
 /**
  * The email address of the employee.
  */
   private String email;
   private Long departmentId;


}
