/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceClassFile;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;

/**
 * binary class file
 */
final class BinaryClassFile
	extends BinaryNode
	implements JavaResourceClassFile
{
	/** JDT class file */
	private final IClassFile classFile;

	/** class file's persistent type */
	private final JavaResourcePersistentType persistentType;


	// ********** construction/initialization **********

	/**
	 * The JDT type gets passed in because the package fragment inspects it
	 * beforehand to determine whether it is "persistable". (We only build
	 * class files for "persistable" types.)
	 */
	BinaryClassFile(JavaResourcePackageFragment parent, IClassFile classFile, IType jdtType) {
		super(parent);
		this.classFile = classFile;
		this.persistentType = this.buildPersistentType(jdtType);
	}

	private JavaResourcePersistentType buildPersistentType(IType jdtType) {
		return new BinaryPersistentType(this, jdtType);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.classFile.getElementName());
	}


	// ********** JavaResourceClassFile implementation **********

	public JavaResourcePersistentType getPersistentType() {
		return this.persistentType;
	}

	// TODO
	@Override
	public void update() {
		super.update();
//		this.persistentType.update(this.classFile.getType());
	}

}
