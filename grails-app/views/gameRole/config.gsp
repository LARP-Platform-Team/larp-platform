<%@ page import="ru.srms.larp.platform.sec.permissions.AclConfigGroup; ru.srms.larp.platform.game.roles.GameRole; ru.srms.larp.platform.sec.permissions.GamePermission" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${gameRoleInstance as GameRole}"/>
  <g:set var="acls" value="${acls as List<AclConfigGroup>}"/>
  <g:set var="title" value="Права роли ${subject.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="index"><i class="arrow left grey icon"></i> Назад</ingame:link>
</content>

<content tag="content">

  <div class="ui styled accordion">
    <g:each in="${acls}" var="aclGroup">
      <div class="title"><i class="dropdown icon"></i> ${aclGroup.title}</div>

      <div class="content">
        <table class="ui table">
          <thead><tr>
            <th>Элемент</th>
            <g:each in="${GamePermission.values()}" var="perm">
              <th>${perm.toString()}</th>
            </g:each>
          </tr></thead>
          <tbody>
          <g:each in="${aclGroup.models}" var="aclItem">
            <tr>
              <td>${aclItem.id} -- ${aclItem.title}</td>
              <g:each in="${GamePermission.values()}" var="perm">
                <td>
                  <ingame:remoteLink
                      elementId="permisson-cell-${aclItem.id}-${perm.toString()}"
                      url="[action: 'setPermission', id: params.id, params: [
                          itemId    : aclItem.id,
                          clazz     : aclGroup.clazz.name,
                          permission: perm.toString()
                      ]]"
                      update="[success: 'permisson-cell-' + aclItem.id + '-' + perm.toString(), failure: 'setPermissionError']">

                    <tmpl:permission value="${aclItem.permissions.contains(perm)}"/>
                  </ingame:remoteLink>
                </td>
              </g:each>
            </tr>
          </g:each>
          </tbody>
        </table>
      </div>
    </g:each>
  </div>

  <div class="errors" id="setPermissionError"></div>

</content>
</body>
</html>
