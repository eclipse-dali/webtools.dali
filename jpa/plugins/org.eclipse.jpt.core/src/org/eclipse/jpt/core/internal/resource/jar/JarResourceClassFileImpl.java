/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.jar;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jpt.core.resource.jar.JarResourceClassFile;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragment;
import org.eclipse.jpt.core.resource.jar.JarResourcePersistentType;

/**
 * JAR class file
 */
public class JarResourceClassFileImpl
	extends AbstractJarResourceNode
	implements JarResourceClassFile
{
	/** JDT class file */
	private IClassFile classFile;

	/** class file's persistent type */
	private JarResourcePersistentType persistentType;


	// ********** construction/initialization **********

	public JarResourceClassFileImpl(JarResourcePackageFragment parent, IClassFile classFile) {
		super(parent);
		this.classFile = classFile;
		this.persistentType = this.buildPersistentType();
	}

	protected JarResourcePersistentType buildPersistentType() {
		return new JarResourcePersistentTypeImpl(this, this.classFile.getType());
	}


	// ********** JarResourceNode implementation **********

	@Override
	public void update() {
		super.update();
		// TODO
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.classFile.getElementName());
	}


	// ********** JarResourceClassFile implementation **********

	public IClassFile getClassFile() {
		return this.classFile;
	}

	public JarResourcePersistentType getPersistentType() {
		return this.persistentType;
	}

}
