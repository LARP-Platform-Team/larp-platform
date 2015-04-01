import org.springframework.web.servlet.i18n.SessionLocaleResolver
import ru.srms.larp.platform.sec.permissions.GamePermissionsEvaluator

// Place your Spring DSL code here
beans = {
    localeResolver(SessionLocaleResolver) {
        defaultLocale = new Locale("ru","RU")
        Locale.setDefault(defaultLocale)
    }

    permissionEvaluator(GamePermissionsEvaluator, ref('aclService'), ref('springSecurityService')) {
        objectIdentityRetrievalStrategy = ref('objectIdentityRetrievalStrategy')
        objectIdentityGenerator = ref('objectIdentityRetrievalStrategy')
        sidRetrievalStrategy = ref('sidRetrievalStrategy')
        permissionFactory = ref('aclPermissionFactory')
    }
}
