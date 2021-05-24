package com.xiaoxiang.springboot.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxiang.springboot.model.IEntity;

public class BaseServiceImpl<M extends BaseMapper<T>, T extends IEntity> extends ServiceImpl<M, T>  {

    public boolean isCurrentModelClass(Class<?> clazz) {
        return ObjectUtil.equal(super.currentModelClass(), clazz);
    }


//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list"})
//    public boolean save(T entity) {
//        return super.save(entity);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list", "#entity.id"})
//    public boolean saveOrUpdate(T entity) {
//        return super.saveOrUpdate(entity);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list", "#entity.id"})
//    public boolean saveOrUpdate(T entity, Wrapper<T> queryWrapper) {
//        return super.saveOrUpdate(entity, queryWrapper);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list", "#entity.id"})
//    public boolean updateById(T entity) {
//        return super.updateById(entity);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list"})
//    public boolean update(Wrapper<T> updateWrapper) {
//        return super.update(updateWrapper);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list", "#id"})
//    public boolean update(T entity, Wrapper<T> updateWrapper) {
//        return super.update(entity, updateWrapper);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list", "#id"})
//    public boolean removeById(Serializable id) {
//        return super.removeById(id);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "list"})
//    public boolean removeByIds(Collection<? extends Serializable> idList) {
//        return super.removeByIds(idList);
//    }
//
//    @CacheStoreEvict(value = "entity", keys = {"page:*", "'list:' + #queryWrapper.targetSql", "'one:' + #queryWrapper.targetSql"})
//    public boolean remove(Wrapper<T> queryWrapper) {
//        return super.remove(queryWrapper);
//    }
//
//
//    @CacheStorePut(value = "entity", key = "#id")
//    public T cacheGetById(Serializable id) {
//        return getById(id);
//    }
//
//    @CacheStorePut(value = "entity", key = "'one:' + #queryWrapper.targetSql")
//    public T cacheGetOne(Wrapper<T> queryWrapper) {
//        return getOne(queryWrapper);
//    }
//
//    @CacheStorePut(value = "entity", key = "list")
//    public List<T> cacheList() {
//        return list();
//    }
//
//    @CacheStorePut(value = "entity", key = "'list:' + #queryWrapper.targetSql")
//    public List<T> cacheList(Wrapper<T> queryWrapper) {
//        return list(queryWrapper);
//    }
//
//    @CacheStorePut(value = "entity", key = "'page:' + #page.cacheKey()")
//    public <E extends IPage<T>> E cachePage(E page) {
//        return page(page);
//    }
//
//    @CacheStorePut(value = "entity", key = "'page:' + #page.cacheKey() + ':' + #queryWrapper.targetSql")
//    public <E extends IPage<T>> E cachePage(E page, Wrapper<T> queryWrapper) {
//        return page(page, queryWrapper);
//    }
}
