package ru.srms.larp.platform.sec.permissions

import com.google.common.collect.ImmutableMap
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import ru.srms.larp.platform.game.mail.MailBox
import ru.srms.larp.platform.game.news.NewsFeed
import ru.srms.larp.platform.game.resources.ResourceInstance

enum GamePermission {

  READ(BasePermission.READ, [NewsFeed.class, MailBox.class, ResourceInstance.class], [
      (NewsFeed.class)        : 'Чтение новостей',
      (MailBox.class)         : 'Чтение писем',
      (ResourceInstance.class): 'Просмотр баланса и лога'
  ]),
  WRITE(BasePermission.WRITE, [NewsFeed.class, ResourceInstance.class, MailBox.class],[
      (NewsFeed.class)        : 'Редактирование новостей',
      (MailBox.class)         : 'Редактирование адресной книги',
      (ResourceInstance.class): 'Изменение баланса'
  ]),
  CREATE(BasePermission.CREATE, [NewsFeed.class, MailBox.class, ResourceInstance.class],[
      (NewsFeed.class)        : 'Создание новостей',
      (MailBox.class)         : 'Написание писем',
      (ResourceInstance.class): 'Перевод средств'
  ]),
  DELETE(BasePermission.DELETE, [NewsFeed.class, MailBox.class],[
      (NewsFeed.class)        : 'Удаление новостей',
      (MailBox.class)         : 'Удаление писем'
  ]);

  Permission aclPermission
  Map<Class, String> names = [:]
  List<Class> applicableClasses = []

  GamePermission(Permission aclPermission, List<Class> applicableClasses, Map<Class, String> names) {
    this.aclPermission = aclPermission
    this.names = names
    this.applicableClasses = applicableClasses
  }

  private static final resolveMap = ImmutableMap.of(
      BasePermission.READ, READ,
      BasePermission.WRITE, WRITE,
      BasePermission.CREATE, CREATE,
      BasePermission.DELETE, DELETE);

  public static boolean existsFor(BasePermission permission) {
    return resolveMap.containsKey(permission)
  }

  public static GamePermission valueFor(BasePermission permission) {
    return resolveMap.get(permission)
  }

  public String getNameFor(Class clazz) {
    names.get(clazz) ?: this.toString()
  }

  public boolean isApplicableFor(Class clazz) {
    applicableClasses.contains(clazz)
  }
}
