/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Anselm Brehme, Phillip Schichtel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.cubeisland.engine.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import de.cubeisland.engine.reflect.codec.FileCodec;
import de.cubeisland.engine.reflect.exception.InvalidReflectedObjectException;

public abstract class FileReflected<C extends FileCodec> extends Reflected<C, File>
{
    public final void save(File target)
    {
        if (target == null)
        {
            throw new IllegalArgumentException("A reflected cannot be saved without a valid file!");
        }
        try
        {
            this.save(new FileOutputStream(target));
            this.onSaved(target);
        }
        catch (FileNotFoundException ex)
        {
            throw new InvalidReflectedObjectException("File to save into cannot be accessed!", ex);
        }
    }

    /**
     * Saves this reflected using given OutputStream
     *
     * @param os the OutputStream to write into
     */
    public final void save(OutputStream os)
    {
        this.onSave();
        this.getCodec().saveReflected(this, os);
    }

    public final boolean loadFrom(File source)
    {
        if (this.sertialType == null)
        {
            throw new IllegalArgumentException("The file must not be null in order to load the reflected!");
        }
        if (source.exists())
        {
            try
            {
                this.loadFrom(new FileInputStream(this.sertialType));
            }
            catch (FileNotFoundException e)
            {
                throw new IllegalArgumentException("File to load from cannot be accessed!", e);
            }
            this.onLoaded(source);
            return true;
        }
        this.factory.logger.log(Level.INFO, "Could not load reflected from file! Using default...");
        return false;
    }

    /**
     * Loads the reflected using the given InputStream
     *
     * @param is the InputStream to load from
     */
    public final void loadFrom(InputStream is)
    {
        if (is == null)
        {
            throw new IllegalArgumentException("The input stream must not be null!");
        }
        this.onLoad();
        this.showLoadErrors(this.getCodec().loadReflected(this, is));
    }

    public File getFile()
    {
        return this.getTarget();
    }

    public void setFile(File file)
    {
        this.setTarget(file);
    }
}
