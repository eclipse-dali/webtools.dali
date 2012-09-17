package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.hamcrest.Matcher;

public class ContextMenuHelper
{

    /**
     * Clicks the context menu matching the text.
     *
     * @param text
     *          the text on the context menu.
     * @throws WidgetNotFoundException
     *           if the widget is not found.
     */
    public static void clickContextMenu( final AbstractSWTBot<?> bot, final String... texts )
    {
        final Matcher<?>[] matchers = new Matcher<?>[texts.length];
        for ( int i = 0; i < texts.length; i++ )
        {
            // matchers[i] = allOf( instanceOf( MenuItem.class ), withMnemonic( texts[i] ) );
            matchers[i] = allOf( instanceOf( MenuItem.class ), withRegex( texts[i] ) );
        }

        // show
        final MenuItem menuItem = UIThreadRunnable.syncExec( new WidgetResult<MenuItem>()
        {
            public MenuItem run()
            {
                MenuItem menuItem = null;
                Control control = ( Control ) bot.widget;
                Menu menu = control.getMenu();
                for ( int i = 0; i < matchers.length; i++ )
                {
                    menuItem = show( menu, matchers[i] );
                    if ( menuItem != null )
                    {
                        menu = menuItem.getMenu();
                    }
                    else
                    {
                        hide( menu );
                        break;
                    }
                }

                return menuItem;
            }
        } );
        if ( menuItem == null )
        {
            throw new WidgetNotFoundException( "Could not find menu: " + Arrays.asList( texts ) );
        }

        // click
        click( menuItem );

        // hide
        UIThreadRunnable.syncExec( new VoidResult()
        {
            public void run()
            {
                hide( menuItem.getParent() );
            }
        } );
    }


    private static MenuItem show( final Menu menu, final Matcher<?> matcher )
    {
        if ( menu != null )
        {
            menu.notifyListeners( SWT.Show, new Event() );
            MenuItem[] items = menu.getItems();
            for ( final MenuItem menuItem : items )
            {
                if ( matcher.matches( menuItem ) )
                {
                    return menuItem;
                }
            }
            menu.notifyListeners( SWT.Hide, new Event() );
        }
        return null;
    }


    private static void click( final MenuItem menuItem )
    {
        final Event event = new Event();
        event.time = ( int ) System.currentTimeMillis();
        event.widget = menuItem;
        event.display = menuItem.getDisplay();
        event.type = SWT.Selection;

        UIThreadRunnable.asyncExec( menuItem.getDisplay(), new VoidResult()
        {
            public void run()
            {
                menuItem.notifyListeners( SWT.Selection, event );
            }
        } );
    }


    private static void hide( final Menu menu )
    {
        menu.notifyListeners( SWT.Hide, new Event() );
        if ( menu.getParentMenu() != null )
        {
            hide( menu.getParentMenu() );
        }
    }
}