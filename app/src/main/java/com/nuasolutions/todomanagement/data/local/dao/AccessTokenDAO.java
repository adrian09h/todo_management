package com.nuasolutions.todomanagement.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;

import java.util.List;

@Dao
public interface AccessTokenDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAccessToken(List<AccessTokenEntity> accessTokenEntities);
    @Query("SELECT * FROM AccessTokenEntity")
    List<AccessTokenEntity> getAccessTokens();
}
