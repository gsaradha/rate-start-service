
package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CategoryRepository extends CrudRepository<CategoryTips, Long> {

    @Query(value="""\
           select t.idTips, c.description, c.file_name, c.icon_name, t.tip, t.favorited, t.Category_idCategory_FK 
           from Tips t join Category c on c.idCategory = t.Category_idCategory_FK where c.idCategory=:categoryId
            """, nativeQuery = true)
    List<CategoryTips> fetchCategoryTips(@Param("categoryId") Integer categoryId)

}
