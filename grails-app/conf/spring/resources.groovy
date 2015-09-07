import org.springframework.web.servlet.i18n.SessionLocaleResolver
import ru.srms.larp.platform.DoubleValueConverter
import ru.srms.larp.platform.db.migrations.MigrationCallbacksHandler
import ru.srms.larp.platform.sec.LarpUserDetailService
import ru.srms.larp.platform.sec.permissions.GamePermissionsEvaluator

// Place your Spring DSL code here
beans = {
  localeResolver(SessionLocaleResolver) {
    defaultLocale = new Locale("ru", "RU")
    Locale.setDefault(defaultLocale)
  }

  // found here http://stackoverflow.com/questions/14877602/grails-databinding-with-decimal-delimiter
  "defaultGrailsjava.lang.DoubleConverter"(DoubleValueConverter)

  permissionEvaluator(GamePermissionsEvaluator, ref('aclService'), ref('springSecurityService')) {
    objectIdentityRetrievalStrategy = ref('objectIdentityRetrievalStrategy')
    objectIdentityGenerator = ref('objectIdentityRetrievalStrategy')
    sidRetrievalStrategy = ref('sidRetrievalStrategy')
    permissionFactory = ref('aclPermissionFactory')
  }

  userDetailsService(LarpUserDetailService)

  // TODO does not work. find out why
  migrationCallbacks(MigrationCallbacksHandler)
}
