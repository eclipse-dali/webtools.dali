/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceClassFile;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragment;

/**
 * binary class file
 */
final class BinaryClassFile
	extends BinaryNode
	implements JavaResourceClassFile
{
	/** JDT class file */
	private final IClassFile classFile;

	/** class file's type */
	private final JavaResourceAbstractType type;


	// ********** construction/initialization **********

	/**
	 * The JDT type gets passed in because the package fragment inspects it
	 * beforehand to determine whether it exists and is relevant.
	 */
	BinaryClassFile(JavaResourcePackageFragment parent, IClassFile classFile, IType jdtType) {
		super(parent);
		this.classFile = classFile;
		this.type = this.buildType(jdtType);
	}

	private JavaResourceAbstractType buildType(IType jdtType) {
		try {
			if (jdtType.isEnum()) {
				return new BinaryEnum(this, jdtType);
			}
		}
		catch (JavaModelException e) {
			JptCommonCorePlugin.log(e);
		}
		return new BinaryType(this, jdtType);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.classFile.getElementName());
	}


	// ********** JavaResourceClassFile implementation **********

	public JavaResourceAbstractType getType() {
		return this.type;
	}

	// TODO
	@Override
	public void update() {
		super.update();
//		type(this.classFile.getType());
	}

}
