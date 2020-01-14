# Minijava compiler
# Paul Mabileau


### Supported functionalities:

 * Complete syntax
 * Optional ELSE after IF statement
 * Type checking
 * Already defined symbols error reporting
 * Undefined symbols reporting
 * Unused symbols warning
 * Void, boolean, int and int[] types
 * int[] new array positive size and access bounds checking
 * Method arguments past four


### Unsupported functionalities:

 * Native types implicit promotion casts
 * Undefined method reporting
 * Proper class methods, everything is flattened
 * boolean[] type


NB: The JFlex lexical specification defines the Println token
	in a more flexible way than proposed in the BNF. Instead
	of using {WS}+ between the "System", "out" and "println"
	strings, {Ignored}+ is used, which includes comments and
	white spaces. But allowing this seems to make the number
	of combinations and DNA states explode, so the generation
	of the parser definitely takes its bit of time (~5s).