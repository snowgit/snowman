package com.sun.darkstar.example.snowman.game.task.enumn;

/**
 * <code>ETask</code> defines the enumerations of all <code>ITask</code>.
 * <p>
 * The enumeration of an <code>ITask</code> implies the execution logic type
 * of the <code>ITask</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-03-2008 11:07 EST
 * @version Modified date: 07-02-2008 24:20 EST
 */
public enum ETask {

	;
	
	/**
	 * The <code>ETaskType</code> enumeration.
	 */
	private final ETaskType type;
	
	/**
	 * Constructor of <code>ETask</code>.
	 * @param type The <code>ETaskType</code> enumeration.
	 */
	private ETask(ETaskType type) {
		this.type = type;
	}
	
	/**
	 * Retrieve the type of this task.
	 * @return The <code>ETaskType</code> enumeration.
	 */
	public ETaskType getType() {
		return this.type;
	}
	
	/**
	 * <code>ETaskType</code> defines all types of <code>ITask</code> managed by the
	 * <code>TaskManager</code>.
	 * 
	 * @author Yi Wang (Neakor)
	 * @author Tim Poliquin (Weenahmen)
	 * @version Creation date: 06-02-2008 15:48 EST
	 * @version Modified date: 06-02-2008 15:50 EST
	 */
	public enum ETaskType {
		/**
		 * The real-time task type.
		 */
		RealTime,
		/**
		 * The certified task type.
		 */
		Certified
	}
}