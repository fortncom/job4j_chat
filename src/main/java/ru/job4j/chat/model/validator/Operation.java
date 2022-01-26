package ru.job4j.chat.model.validator;

/**
 * Маркерные интерфейсы для валидации моделей данных.
 * При обьявлении ограничения для поля модели данных
 * в параметре group{Operation.OnCreate.class} указываем
 * для каких операций будет срабатывать данная валидация.
 * Над методом контроллера где эта валидация
 * применяется ставим @Validated(Operation.OnCreate.class).
 * Не аннотированные методы контроллера использующие
 * ту же самую модель валидировать данное поле не будут.
 *
 * @author  Gromov Anatoliy
 * @version 1.0
 */

public class Operation {

    public interface OnCreate { }

    public interface OnDelete { }

    public interface OnUpdate { }

    public interface OnPatch { }
}
