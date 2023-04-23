package jeudesfourmis.vue;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField
{
    protected int min;
    protected int max;

    public CustomTextField(int min, int max)
    {
        super();
        this.min = min;
        this.max = max;
    }

    @Override
    public void replaceText(int start, int end, String text)
    {
        if(validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if(validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return text.matches("[" + min + "-" + max + "]");
    }
}
