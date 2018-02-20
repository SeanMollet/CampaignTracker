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


}
