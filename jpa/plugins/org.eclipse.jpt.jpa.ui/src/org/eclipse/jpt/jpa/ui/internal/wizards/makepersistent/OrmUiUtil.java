/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class OrmUiUtil
{	
	/**
	 * Launch the class chooser to allow user to pick a java class
	 * @return the class user picks to null of the class chooser dlg was canceled
	 */
	public static String selectJavaClass(IProject project)
	{
		String javaClass = null;
		
		Display display = Display.getDefault();
        Shell shell = display.getActiveShell();
        IJavaSearchScope sc 
            = SearchEngine.createJavaSearchScope( new IJavaElement[] 
                                                  { 
                                                      JavaCore.create(project) 
                                                  } );            
        
        try
        {                
        	FilteredTypesSelectionDialog dlg = (FilteredTypesSelectionDialog)JavaUI.createTypeDialog( shell, new ProgressMonitorDialog( shell ), sc, IJavaElementSearchConstants.CONSIDER_CLASSES, false );
            dlg.setTitle(JptJpaUiMakePersistentMessages.CHOOSE_TYPE); 
            dlg.setBlockOnOpen( true );
            
            if ( dlg.open() == Window.OK )
            {
                Object[] sels = dlg.getResult();
                if (sels == null) 
                    return null;
                assert( sels[0] instanceof IType );
                IType type = (IType)sels[0];
                javaClass = type.getFullyQualifiedName('.');
            }
        }
        catch ( JavaModelException jme )
        {
            // todo -- notify user            
        }
        return javaClass;
    }
	
}
