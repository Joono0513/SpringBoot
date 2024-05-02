package com.aloha.springdi.bean;

import org.springframework.stereotype.Component;

//@Data
@Component
public class Student extends Person{

    private int studentId;
    private String grade;
    
    // Ctrl + . : quick fix
    public Student() {
        super();
        this.studentId = 1001;
        this.grade = "1";
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", grade=" + grade + ", getAge()=" + getAge() + ", getName()="
                + getName() + "]";
    }

}
