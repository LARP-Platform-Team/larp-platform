import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder as SCH
import ru.srms.larp.platform.GameAclService
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.game.mail.Letter
import ru.srms.larp.platform.game.news.NewsFeed
import ru.srms.larp.platform.game.news.NewsItem
import ru.srms.larp.platform.game.roles.CharacterRole
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.SpringRole
import ru.srms.larp.platform.sec.SpringUser
import ru.srms.larp.platform.sec.SpringUserSpringRole

import static org.springframework.security.acls.domain.BasePermission.*

class BootStrap {

    def aclService
    def aclUtilService
    GameAclService gameAclService

    def init = { servletContext ->
        if(Letter.count == 0)
        {
            new Letter(subject: "hi", text: "howdy?").save()
            new Letter(subject: "привет", text: "какчо?").save()

            def roleAdmin = new SpringRole(authority: 'ROLE_ADMIN').save()
            def roleGm = new SpringRole(authority: 'ROLE_GM').save()
            def roleAclChanger = new SpringRole(authority: 'ROLE_ACL_CHANGE_DETAILS').save()

            def admin = new SpringUser(username: 'admin', password: 'a').save()
            SpringUserSpringRole.create(admin, roleAdmin)
            SpringUserSpringRole.create(admin, roleAclChanger)

            def gm1 = new SpringUser(username: "gm1", password: "a").save()
            SpringUserSpringRole.create(gm1, roleGm)
            SpringUserSpringRole.create(gm1, roleAclChanger)
            def gm2 = new SpringUser(username: "gm2", password: "a").save()
            SpringUserSpringRole.create(gm2, roleGm)
            SpringUserSpringRole.create(gm2, roleAclChanger)

            def usr1 = new SpringUser(username: "usr1", password: "a").save()
            def usr2 = new SpringUser(username: "usr2", password: "a").save()

            Game game1 = new Game(title: "Красная шапочка", alias: "the-red-hat",
                    overview: "Игра по знаменитой сказке.")
                    .addToMasters(gm1)
                    .save()


//            have to be authenticated as an admin to create ACLs
            SCH.context.authentication = new UsernamePasswordAuthenticationToken(
                    'admin', 'a', AuthorityUtils.createAuthorityList('ROLE_ADMIN'))

            aclUtilService.addPermission(game1, gm1.username, ADMINISTRATION)

            def redHat = new GameCharacter(name: "красная шапочка", game: game1, alias: 'red-hat').save()
            def wolf = new GameCharacter(name: "волк", game: game1, alias: 'wolf').save()
            aclService.createAcl(new ObjectIdentityImpl(redHat))
            aclService.createAcl(new ObjectIdentityImpl(wolf))
            game1.save()

            // TODO проверить - кажется, не сохраняется
            usr1.addToCharacters(wolf).save()
            aclUtilService.addPermission(wolf, usr1.username, READ)
            aclUtilService.addPermission(wolf, usr1.username, WRITE)


            def feed = new NewsFeed(game: game1, title: "Новости леса").save()
            gameAclService.createAcl(feed)

            def now = new Date().getTime()
            new NewsItem(feed: feed, title: "Новость раз", text: "Все умерли", created: new Date(now - 2400000)).save()



            // game 2
            Game game2 = new Game(title: "Камелот", alias: "CamelotAge77",
                    overview: "Игра о славных ряцарях камелота.")
            game2.addToMasters(gm1).addToMasters(gm2).save()
            aclUtilService.addPermission(game2, gm2.username, ADMINISTRATION)
            aclUtilService.addPermission(game2, gm1.username, ADMINISTRATION)

            def king = new GameCharacter(name: "Король Артур", game: game2, player: usr1, alias: 'arthur').save()
            def lancelot = new GameCharacter(name: "Ланселот", game: game2, player: usr2, alias: 'lancelot').save()
            def merlin = new GameCharacter(name: "Мерлин", game: game2, player: usr2, alias: 'merlin').save()

            aclUtilService.addPermission(king, usr1.username, READ)
            aclUtilService.addPermission(king, usr1.username, WRITE)
            aclUtilService.addPermission(lancelot, usr2.username, READ)
            aclUtilService.addPermission(lancelot, usr2.username, WRITE)
            aclUtilService.addPermission(merlin, usr2.username, READ)
            aclUtilService.addPermission(merlin, usr2.username, WRITE)

            game2.save()

            // новости
            feed = new NewsFeed(game: game2, title: "Круглый стол").save()
            gameAclService.createAcl(feed)


            now = new Date().getTime()
            new NewsItem(feed: feed, title: "Новость раз", text: "Все хорошо", created: new Date(now - 2400000)).save()
            new NewsItem(feed: feed, title: "Новость два", text: "Все плохо", created: new Date(now - 3600000)).save()
            new NewsItem(feed: feed, title: "Новость три", text: "Все норм", created: new Date(now - 1200000)).save()

            def mFeed = new NewsFeed(game: game2, title: "Новости магии").save()
            gameAclService.createAcl(mFeed)

            new NewsItem(feed: mFeed, title: "Новость магии раз", text: "Все хорошо у магов").save()
            new NewsItem(feed: mFeed, title: "Новость магии два", text: "Все хорошо!").save()
            new NewsItem(feed: mFeed, title: "Новость магии три", text: "Все хорошо!!! Дада.").save()

            // роли
            def r1 = new GameRole(title: "ФСБ", game: game2).save(flush: true)
            new GameRole(title: 'Стажер ФСБ', game: game2, parent: r1).save()
            def r2 = new GameRole(title: 'Офицер ФСБ', game: game2, parent: r1).save()
            new GameRole(title: 'Начальник ФСБ', game: game2, parent: r2).save()

            new GameRole(title: "Вампир", game: game2).save()
            new GameRole(title: "Человек", game: game2).save()
            def werewolf = new GameRole(title: "Оборотень", game: game2).save()

            CharacterRole.create(lancelot, r1)
            CharacterRole.create(lancelot, r2)
            CharacterRole.create(merlin, r1)

            aclUtilService.addPermission(mFeed, r1.authority, READ)
        }

    }
    def destroy = {
    }
}
