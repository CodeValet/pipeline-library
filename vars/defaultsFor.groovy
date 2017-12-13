/**
 * This step applies the default Pipeline for the given langauge definition.
 */

def call(String platform) {
    switch (platform) {
        case 'java':
            echo 'Hello World'
            break

        default:
            fail "I do not know what the defaults for `${platform}` should be :("
            break
    }
}
