/*******************************************************************************
* Copyright (c) 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1;


/**
 * parameter mode
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public enum ParameterMode2_1
{
	IN(
		org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1.IN,
		org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1.IN
		),
	INOUT(
			org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1.INOUT,
			org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1.INOUT
		),
	OUT(
			org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1.OUT,
			org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1.OUT
		),
	REF_CURSOR(
			org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1.REF_CURSOR,
			org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1.REF_CURSOR
		);


	private org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1 javaParameterMode;
	private org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 ormParameterMode;

	ParameterMode2_1(
			org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1 javaParameterMode,
			org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 ormParameterMode) {
		if (javaParameterMode == null) {
			throw new NullPointerException();
		}
		if (ormParameterMode == null) {
			throw new NullPointerException();
		}
		this.javaParameterMode = javaParameterMode;
		this.ormParameterMode = ormParameterMode;
	}

	public org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1 getJavaParameterMode() {
		return this.javaParameterMode;
	}

	public org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 getOrmParameterMode() {
		return this.ormParameterMode;
	}


	// ********** static methods **********

	public static ParameterMode2_1 fromJavaResourceModel(org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1 javaParameterMode) {
		return (javaParameterMode == null) ? null : fromJavaResourceModel_(javaParameterMode);
	}

	private static ParameterMode2_1 fromJavaResourceModel_(org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1 javaParameterMode) {
		for (ParameterMode2_1 parameterMode : ParameterMode2_1.values()) {
			if (parameterMode.getJavaParameterMode() == javaParameterMode) {
				return parameterMode;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1 toJavaResourceModel(ParameterMode2_1 parameterMode) {
		return (parameterMode == null) ? null : parameterMode.getJavaParameterMode();
	}


	public static ParameterMode2_1 fromOrmResourceModel(org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 ormParameterMode) {
		return (ormParameterMode == null) ? null : fromOrmResourceModel_(ormParameterMode);
	}

	private static ParameterMode2_1 fromOrmResourceModel_(org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 ormParameterMode) {
		for (ParameterMode2_1 parameterMode : ParameterMode2_1.values()) {
			if (parameterMode.getOrmParameterMode() == ormParameterMode) {
				return parameterMode;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1 toOrmResourceModel(ParameterMode2_1 parameterMode) {
		return (parameterMode == null) ? null : parameterMode.getOrmParameterMode();
	}
}
