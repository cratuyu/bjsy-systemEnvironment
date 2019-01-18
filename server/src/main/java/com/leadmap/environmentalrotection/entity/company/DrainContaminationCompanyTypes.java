package com.leadmap.environmentalrotection.entity.company;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/10 16:06
 */
@Entity
@Table(name = "DrainContaminationCompanyTypes")
public class DrainContaminationCompanyTypes implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    @Column(name = "name")
    private String name;
}
