package com.example.txrole.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "tx_role")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    private String id;

    private String roleName;
}
