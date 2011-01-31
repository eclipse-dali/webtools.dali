/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.internal.platform.generic.ClassRefItemLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.JarFileRefItemLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.MappingFileRefItemLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.PersistenceItemLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.PersistenceUnitItemLabelProvider;

public class PersistenceItemLabelProviderFactory
	implements ItemLabelProviderFactory
{
	public ItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		if (item instanceof Persistence) {
			return new PersistenceItemLabelProvider((Persistence) item, contentAndLabelProvider);
		}
		else if (item instanceof PersistenceUnit) {
			return new PersistenceUnitItemLabelProvider((PersistenceUnit) item, contentAndLabelProvider);	
		}
		else if (item instanceof MappingFileRef) {
			return new MappingFileRefItemLabelProvider((MappingFileRef) item, contentAndLabelProvider);	
		}
		else if (item instanceof ClassRef) {
			return new ClassRefItemLabelProvider((ClassRef) item, contentAndLabelProvider);	
		}
		else if (item instanceof JarFileRef) {
			return new JarFileRefItemLabelProvider((JarFileRef) item, contentAndLabelProvider);
		}
		return null;
	}
}
