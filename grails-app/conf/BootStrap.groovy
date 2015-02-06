import org.apache.shiro.crypto.hash.Sha256Hash
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.mail.Letter
import ru.srms.larp.platform.sec.ShiroUser

class BootStrap {

    def init = { servletContext ->
        if(Letter.count == 0)
        {
            new Letter(subject: "hi", text: "howdy?").save()
            new Letter(subject: "привет", text: "какчо?").save()

            def admin = new ShiroUser(username: "admin", passwordHash: new Sha256Hash("admin1").toHex())
            admin.addToPermissions("*:*")
            admin.save()

            def gm1 = new ShiroUser(username: "gm1", passwordHash: new Sha256Hash("admin1").toHex())
            gm1.save()

            def gm2 = new ShiroUser(username: "gm2", passwordHash: new Sha256Hash("admin1").toHex())
            gm2.save()

            def usr1 = new ShiroUser(username: "usr1", passwordHash: new Sha256Hash("admin1").toHex())
            usr1.save()

            def usr2 = new ShiroUser(username: "usr2", passwordHash: new Sha256Hash("admin1").toHex())
            usr2.save()

            Game game1 = new Game(title: "Красная шапочка")
            game1.addToMasters(gm1)
            game1.save()

            def redHat = new GameCharacter(name: "красная шапочка", game: game1)
            redHat.save()
            def wolf = new GameCharacter(name: "волк", game: game1)
            wolf.save()

            game1.save()

            usr1.addToCharacters(wolf)
            usr1.save()


            Game game2 = new Game(title: "Камелот")
            game2.addToMasters(gm1).addToMasters(gm2)
            game2.save()

            def king = new GameCharacter(name: "Король Артур", game: game2, player: usr1).save()
            def lancelot = new GameCharacter(name: "Ланселот", game: game2, player: usr2).save()
            def merlin = new GameCharacter(name: "Мерлин", game: game2, player: usr2).save()

            game2.save()
//            merlin.player = usr2
//            merlin.save()

        }

    }
    def destroy = {
    }
}
