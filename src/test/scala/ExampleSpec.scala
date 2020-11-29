import zio.test._
import zio.test.Assertion._
import zio.ZIO


object ExampleSpec extends DefaultRunnableSpec {
    def spec = suite("example tests")(
        test("1+1 should equal 2") {
            assert(1+1)(equalTo(2))
        },
        testM("ZIO effect with specified value"){
            assertM(ZIO.succeed(1+1))(equalTo(2))
        }
    )
}