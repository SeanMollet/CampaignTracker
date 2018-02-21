using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace CampaignData
{
    public static class StringExtension
    {
        public static string StripPunctuation(this string s)
        {
            var sb = new StringBuilder();
            foreach (char c in s)
            {
                if (!char.IsPunctuation(c))
                    sb.Append(c);
            }
            return sb.ToString();
        }
        public static string StripPunctuationAndSpaces(this string s)
        {
            var sb = new StringBuilder();
            foreach (char c in s)
            {
                if (!char.IsPunctuation(c) && !char.IsWhiteSpace(c))
                    sb.Append(c);
            }
            return sb.ToString();
        }

    }

    public class BindableString
    {
        public String Value { get; set; }
        public static explicit operator BindableString(string s)
        {
            return new BindableString { Value = s };
        }
        public static explicit operator string(BindableString b)
        {
            return b.Value;
        }
    }

    public class HPAppearance
    {
        public static string Appearance(int CurrentHP,int maxHP)
        {
            float health = (float)CurrentHP / (float)maxHP;
            if (health >= 1.0f)
            {
                return "Healthy";
            }
            if (health >= 0.9f)
            {
                return "Bruised";
            }
            if (health >= 0.7f)
            {
                return "Banged Up";
            }
            if (health >= 0.5f)
            {
                return "Bloody";
            }
            if (health >= 0.3f)
            {
                return "Ragged";
            }
            if (health >= 0.1f)
            {
                return "Bloody as hell";
            }
            if (health < 0.1f)
            {
                return "Death's door";
            }
            return "Excellent";
        }
    }

}
