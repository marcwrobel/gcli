package fr.marcwrobel.gcli

/**
 * Store CLI texts to be used with CliBuilder.
 */
interface CliTexts {

  /**
   * Command name.
   * @return a non-null String
   */
  String name()

  /**
   * Command arguments.
   * @return a non-null list of String (may be empty)
   */
  List<String> args()

  /**
   * Command footer.
   * @return a String (may be null)
   */
  String footer()

  /**
   * Build command usage based on CLI texts.
   * @return
   */
  String usage()

}
