/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IProjectValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

/**
 * This class is referenced in the JAXB extension for the
 * WTP validator extension point.
 */
public class JaxbValidator
		extends AbstractValidator
		implements IValidator {

	public static final String RELATIVE_MARKER_ID = "jaxbProblemMarker";  //$NON-NLS-1$

	/**
	 * The identifier for the JAXB validation marker
	 * (value <code>"org.eclipse.jpt.jaxb.core.jaxbProblemMarker"</code>).
	 * <p>
	 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.resources.markers</code>.
	 */
	public static final String MARKER_ID = JptJaxbCorePlugin.instance().getPluginID() + '.' + RELATIVE_MARKER_ID;

	public JaxbValidator() {
		super();
	}


	// ********** IValidator implementation **********

	public void validate(IValidationContext context, IReporter reporter) {
		this.validate(reporter, ((IProjectValidationContext) context).getProject());
	}

	public void cleanup(IReporter reporter) {
		// nothing to do
	}


	// ********** AbstractValidator implementation **********

	@Override
	public ValidationResult validate(IResource resource, int kind, ValidationState state, IProgressMonitor monitor) {
		if (resource.getType() != IResource.FILE) {
			return null;
		}
		ValidationResult result = new ValidationResult();
		IReporter reporter = result.getReporter(monitor);
		IProject project = resource.getProject();
		this.clearMarkers(project);
		result.setSuspendValidation(project);
		this.validate(reporter, project);
		return result;
	}


	// ********** internal **********

	private void clearMarkers(IProject project) {
		try {
			this.clearMarkers_(project);
		} catch (CoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
		}
	}

	private void clearMarkers_(IProject project) throws CoreException {
		IMarker[] markers = project.findMarkers(JaxbValidator.MARKER_ID, true, IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			marker.delete();
		}
	}

	private void validate(IReporter reporter, IProject project) {
		for (IMessage message : this.buildValidationMessages(reporter, project)) {
			// TODO check preferences for IGNORE
//			if (JpaValidationPreferences.problemIsNotIgnored(project, message.getId())) {
				reporter.addMessage(this, message);
//			}

		}
	}

	private Iterable<IMessage> buildValidationMessages(IReporter reporter, IProject project) {
		JaxbProject jaxbProject = this.getJaxbProject(project);
		if (jaxbProject != null) {
			return jaxbProject.getValidationMessages(reporter);
		}
		return new SingleElementIterable<IMessage>(
				DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.NO_JAXB_PROJECT,
						project));
	}

	private JaxbProject getJaxbProject(IProject project) {
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager();
		return (jaxbProjectManager == null) ? null : jaxbProjectManager.getJaxbProject(project);
	}

	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : this.getJaxbWorkspace().getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}
}
