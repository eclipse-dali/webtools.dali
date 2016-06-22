/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;

/**
 * Standard adapter for retrieving a {@link JpaFile JPA file} model
 * with change notification when the JPA file is created or destroyed
 * (or its JPA project is created or destroyed):
 * <pre>
 * IFile file = (IFile) ResourcesPlugin.getWorkspace().getRoot().findMember("Foo.java");
 * JpaFileModel jpaFileModel = (JpaFileModel) file.getAdapter(JpaFileModel.class);
 * JpaFile jpaFile = jpaFileModel.getValue();
 * </pre>
 * @see org.eclipse.jpt.jpa.ui.internal.FileAdapterFactory
 */
public interface JpaFileModel
	extends PropertyValueModel<JpaFile>
{
	// simply an interface we can use in an adapter definition
}
