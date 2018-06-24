package com.ratestart.integrator.repo

import org.hibernate.sql.Delete
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface FavoriteRepository extends CrudRepository<Favorite, Long> {

    @Query(value="""\
			select f.idFavorites,f.User_idUser_FK,f.Tip_idTip_FK, t.tip, f.Category_idCategory_FK
            from Tips t join Favorites f on t.idTips=f.Tip_idTip_FK where f.User_idUser_FK=:userId
            """, nativeQuery = true)
    List<Favorite> fetchFavorites(@Param("userId") Long userId)

    @Modifying
    @Transactional
    @Query(value="""\
			delete from Favorites where idFavorites=:favoriteId
            """, nativeQuery = true)
    void deleteFavorite(@Param("favoriteId") Long favoriteId)

}
