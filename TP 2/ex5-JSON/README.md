JSON parser and pretty-printer
------------------------------

The JSON parser generating files are JSON.jflex and JSON.cup.
JsonPrettyPrintingUtils.java describes a Java class used in
JSON.cup to help pretty-print without too much code in the
specification. The result is only displayed if the parsing does
no fail.
