import hi.Letter

class BootStrap {

    def init = { servletContext ->
        if(Letter.count == 0)
        {
            new Letter(subject: "hi", text: "howdy?").save()
            new Letter(subject: "привет", text: "какчо?").save()
        }
    }
    def destroy = {
    }
}
