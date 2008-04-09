/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.schema.generation;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.FileChooserPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  DdlGenerationLocationComposite
 */
public class DdlGenerationLocationComposite extends AbstractPane<SchemaGeneration>
{
	public DdlGenerationLocationComposite(AbstractPane<? extends SchemaGeneration> parentPane,
	                                      Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		new FileChooserPane<SchemaGeneration>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<SchemaGeneration, String>(getSubjectHolder(), "SchemaGeneration.") {
					@Override
					protected String buildValue_() {
						return "";
					}

					@Override
					protected void setValue_(String value) {
					}
				};
			}

			@Override
			protected String dialogMessage() {
				return "TODO";
			}

			@Override
			protected String dialogTitle() {
				return "Generation File";
			}

			@Override
			protected IResource getDialogInput() {
				return null;
			}

			@Override
			protected String labelText() {
				return "DDL Generation Location:";
			}
		};
	}
}
