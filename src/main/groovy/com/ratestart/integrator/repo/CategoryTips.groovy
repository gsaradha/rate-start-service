package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.BooleanConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "Tips")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class CategoryTips implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idTips")
    Long idTips

    @Basic(optional = false)
    @Column(name = "tip")
    String tip

    @Basic(optional = false)
    @Column(name = "favorited")
    @Convert(converter = BooleanConverter)
    Boolean favorited

    @Basic(optional = false)
    @Column(name = "Category_idCategory_FK")
    Integer categoryId

    @Basic(optional = false)
    @Column(name = "description")
    String description

    @Basic(optional = false)
    @Column(name = "file_name")
    String fileName

    @Basic(optional = false)
    @Column(name = "icon_name")
    String iconName


}
