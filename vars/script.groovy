/**
 * This step just exists to override the script {} block for /most/ users in
 * Declarative Pipeline
 */
def call(Closure body) {
    echo "I know you want me to run some Scripted Pipeline"
    echo "But I'm not going to"
}
