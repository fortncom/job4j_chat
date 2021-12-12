package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommonService<T> {

    List<T> findAll();

    Optional<T> findById(int id);

    T create(T t);

    void update(T t);

    void delete(int id);

    default T partialFieldReplacement(T target, T donor)
            throws InvocationTargetException, IllegalAccessException {
        Method[] methods = target.getClass().getDeclaredMethods();
        Map<String, Method> namePerMethod = new HashMap<>();
        for (Method method: methods) {
            String name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                Method getMethod = namePerMethod.get(name);
                Method setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid properties mapping");
                }
                Object newValue = getMethod.invoke(donor);
                if (newValue != null) {
                    setMethod.invoke(target, newValue);
                }
            }
        }
        return target;
    }

}
