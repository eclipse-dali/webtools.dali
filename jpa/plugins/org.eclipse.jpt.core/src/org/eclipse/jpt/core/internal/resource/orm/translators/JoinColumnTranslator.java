/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.BooleanTranslator;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class JoinColumnTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public JoinColumnTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlJoinColumnImpl();
	}

	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createReferencedColumnNameTranslator(),
			createUniqueTranslator(),
			createNullableTranslator(),
			createInsertableTranslator(),
			createUpdatableTranslator(),
			createColumnDefinitionTranslator(),
			createTableTranslator(),
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlNamedColumn_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createReferencedColumnNameTranslator() {
		return new Translator(REFERENCED_COLUMN_NAME, ORM_PKG.getXmlJoinColumn_ReferencedColumnName(), DOM_ATTRIBUTE);
	}
	
	private Translator createUniqueTranslator() {
		return new BooleanTranslator(UNIQUE, ORM_PKG.getXmlAbstractColumn_Unique(), DOM_ATTRIBUTE);
	}
	
	private Translator createNullableTranslator() {
		return new BooleanTranslator(NULLABLE, ORM_PKG.getXmlAbstractColumn_Nullable(), DOM_ATTRIBUTE);
	}
	
	private Translator createInsertableTranslator() {
		return new BooleanTranslator(INSERTABLE, ORM_PKG.getXmlAbstractColumn_Insertable(), DOM_ATTRIBUTE);
	}
	
	private Translator createUpdatableTranslator() {
		return new BooleanTranslator(UPDATABLE, ORM_PKG.getXmlAbstractColumn_Updatable(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnDefinitionTranslator() {
		return new Translator(COLUMN_DEFINITION, ORM_PKG.getXmlNamedColumn_ColumnDefinition(), DOM_ATTRIBUTE);
	}
	
	private Translator createTableTranslator() {
		return new Translator(TABLE, ORM_PKG.getXmlAbstractColumn_Table(), DOM_ATTRIBUTE);
	}
}
