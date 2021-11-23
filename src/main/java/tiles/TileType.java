package tiles;

public enum TileType {

	PASSABLE(' '), NOT_PASSABLE('X'), PLAYER('P'), PORTALZERO('0'), PORTALONE('1'), PORTALTWO('2'), PORTALTHREE('3'),
	PORTALFOUR('4'), PORTALFIVE('5'), PORTALSIX('6'), PORTALSEVEN('7'), PORTALEIGHT('8'), PORTALNINE('9');

	static final String INVALID_CHARACTER_PROVIDED_MESSAGE = "Invalid character provided: ";

	public static TileType getTileTypeByChar(char ch) {

		for (TileType type : TileType.values()) {
			if (type.asChar == ch) {
				return type;
			}
		}

		throw new IllegalArgumentException(INVALID_CHARACTER_PROVIDED_MESSAGE + ch);
	}

	private final char asChar;

	private TileType(char asChar) {
		this.asChar = asChar;
	}

}
