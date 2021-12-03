package tiles;

public enum TileType {

	PASSABLE(' '), NOT_PASSABLE('X'), PLAYER('P'), PORTAL_ZERO('0'), PORTAL_ONE('1'), PORTAL_TWO('2'),
	PORTAL_THREE('3'), PORTAL_FOUR('4'), PORTAL_FIVE('5'), PORTAL_SIX('6'), PORTAL_SEVEN('7'), PORTAL_EIGHT('8'),
	PORTAL_NINE('9');

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
