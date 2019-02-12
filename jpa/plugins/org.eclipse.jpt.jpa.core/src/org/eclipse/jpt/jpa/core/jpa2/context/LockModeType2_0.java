/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

/**
 * LockMode Type
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public enum LockModeType2_0 {

	READ(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.READ,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.READ
		),
	WRITE(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.WRITE,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.WRITE
		),
	OPTIMISTIC(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.OPTIMISTIC,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.OPTIMISTIC
		),
	OPTIMISTIC_FORCE_INCREMENT(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.OPTIMISTIC_FORCE_INCREMENT,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.OPTIMISTIC_FORCE_INCREMENT
		),
	PESSIMISTIC_READ(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.PESSIMISTIC_READ,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.PESSIMISTIC_READ
		),
	PESSIMISTIC_WRITE(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.PESSIMISTIC_WRITE,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.PESSIMISTIC_WRITE
		),
	PESSIMISTIC_FORCE_INCREMENT(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.PESSIMISTIC_FORCE_INCREMENT,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.PESSIMISTIC_FORCE_INCREMENT
		),
	NONE(
		org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0.NONE,
		org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.NONE
		);


	private org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0 javaLockModeType;
	private org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0 ormLockModeType;

	LockModeType2_0(org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0 javaLockModeType, org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0 ormLockModeType) {
		if (javaLockModeType == null) {
			throw new NullPointerException();
		}
		if (ormLockModeType == null) {
			throw new NullPointerException();
		}
		this.javaLockModeType = javaLockModeType;
		this.ormLockModeType = ormLockModeType;
	}

	public org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0 getJavaLockModeType() {
		return this.javaLockModeType;
	}

	public org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0 getOrmLockModeType() {
		return this.ormLockModeType;
	}


	// ********** static methods **********

	public static LockModeType2_0 fromJavaResourceModel(org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0 javaLockModeType) {
		return (javaLockModeType == null) ? null : fromJavaResourceModel_(javaLockModeType);
	}

	private static LockModeType2_0 fromJavaResourceModel_(org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0 javaLockModeType) {
		for (LockModeType2_0 lockModeType : LockModeType2_0.values()) {
			if (lockModeType.getJavaLockModeType() == javaLockModeType) {
				return lockModeType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0 toJavaResourceModel(LockModeType2_0 lockModeType) {
		return (lockModeType == null) ? null : lockModeType.getJavaLockModeType();
	}


	public static LockModeType2_0 fromOrmResourceModel(org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0 ormLockModeType) {
		return (ormLockModeType == null) ? null : fromOrmResourceModel_(ormLockModeType);
	}

	private static LockModeType2_0 fromOrmResourceModel_(org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0 ormLockModeType) {
		for (LockModeType2_0 lockModeType : LockModeType2_0.values()) {
			if (lockModeType.getOrmLockModeType() == ormLockModeType) {
				return lockModeType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0 toOrmResourceModel(LockModeType2_0 lockModeType) {
		return (lockModeType == null) ? null : lockModeType.getOrmLockModeType();
	}

}
