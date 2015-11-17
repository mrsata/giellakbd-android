LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libvoikko
LOCAL_CPP_FEATURES += exceptions rtti
LOCAL_CPP_EXTENSION := .cpp .cc
LOCAL_CFLAGS := -DHAVE_VFST -DHAVE_EXPERIMENTAL_VFST -DHAVE_MMAP -DPOSIX
LOCAL_C_INCLUDES := $(LOCAL_PATH)/corevoikko/libvoikko/src
LOCAL_SRC_FILES := \
	corevoikko/libvoikko/src/character/charset.cpp \
	corevoikko/libvoikko/src/character/SimpleChar.cpp \
	corevoikko/libvoikko/src/compatibility/interface.cpp \
	corevoikko/libvoikko/src/fst/Configuration.cpp \
	corevoikko/libvoikko/src/fst/Transducer.cpp \
	corevoikko/libvoikko/src/fst/UnweightedTransducer.cpp \
	corevoikko/libvoikko/src/fst/WeightedConfiguration.cpp \
	corevoikko/libvoikko/src/fst/WeightedTransducer.cpp \
	corevoikko/libvoikko/src/grammar/Analysis.cpp \
	corevoikko/libvoikko/src/grammar/CacheEntry.cpp \
	corevoikko/libvoikko/src/grammar/error.cpp \
	corevoikko/libvoikko/src/grammar/FinnishAnalysis.cpp \
	corevoikko/libvoikko/src/grammar/FinnishGrammarChecker.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/CapitalizationCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/checks.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/CompoundVerbCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/MissingVerbCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/NegativeVerbCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/ParagraphCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/SentenceCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/SidesanaCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine/VfstAutocorrectCheck.cpp \
	corevoikko/libvoikko/src/grammar/FinnishRuleEngine.cpp \
	corevoikko/libvoikko/src/grammar/GcCache.cpp \
	corevoikko/libvoikko/src/grammar/GrammarChecker.cpp \
	corevoikko/libvoikko/src/grammar/GrammarCheckerFactory.cpp \
	corevoikko/libvoikko/src/grammar/interface.cpp \
	corevoikko/libvoikko/src/grammar/NullGrammarChecker.cpp \
	corevoikko/libvoikko/src/grammar/Paragraph.cpp \
	corevoikko/libvoikko/src/grammar/RuleEngine.cpp \
	corevoikko/libvoikko/src/grammar/Sentence.cpp \
	corevoikko/libvoikko/src/grammar/VoikkoGrammarError.cpp \
	corevoikko/libvoikko/src/hyphenator/AnalyzerToFinnishHyphenatorAdapter.cpp \
	corevoikko/libvoikko/src/hyphenator/Hyphenator.cpp \
	corevoikko/libvoikko/src/hyphenator/HyphenatorFactory.cpp \
	corevoikko/libvoikko/src/hyphenator/interface.cpp \
	corevoikko/libvoikko/src/morphology/Analysis.cpp \
	corevoikko/libvoikko/src/morphology/Analyzer.cpp \
	corevoikko/libvoikko/src/morphology/AnalyzerFactory.cpp \
	corevoikko/libvoikko/src/morphology/FinnishVfstAnalyzer.cpp \
	corevoikko/libvoikko/src/morphology/interface.cpp \
	corevoikko/libvoikko/src/morphology/NullAnalyzer.cpp \
	corevoikko/libvoikko/src/morphology/VfstAnalyzer.cpp \
	corevoikko/libvoikko/src/sentence/interface.cpp \
	corevoikko/libvoikko/src/sentence/Sentence.cpp \
	corevoikko/libvoikko/src/setup/BackendProperties.cpp \
	corevoikko/libvoikko/src/setup/Dictionary.cpp \
	corevoikko/libvoikko/src/setup/DictionaryException.cpp \
	corevoikko/libvoikko/src/setup/DictionaryFactory.cpp \
	corevoikko/libvoikko/src/setup/DictionaryLoader.cpp \
	corevoikko/libvoikko/src/setup/interface.cpp \
	corevoikko/libvoikko/src/setup/LanguageTag.cpp \
	corevoikko/libvoikko/src/setup/setup.cpp \
	corevoikko/libvoikko/src/setup/V5DictionaryLoader.cpp \
	corevoikko/libvoikko/src/spellchecker/AnalyzerToSpellerAdapter.cpp \
	corevoikko/libvoikko/src/spellchecker/FinnishSpellerTweaksWrapper.cpp \
	corevoikko/libvoikko/src/spellchecker/FixedResultSpeller.cpp \
	corevoikko/libvoikko/src/spellchecker/spell.cpp \
	corevoikko/libvoikko/src/spellchecker/Speller.cpp \
	corevoikko/libvoikko/src/spellchecker/SpellerCache.cpp \
	corevoikko/libvoikko/src/spellchecker/SpellerFactory.cpp \
	corevoikko/libvoikko/src/spellchecker/SpellUtils.cpp \
	corevoikko/libvoikko/src/spellchecker/SpellWithPriority.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/Suggestion.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorCaseChange.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorDeleteTwo.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorDeletion.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorFactory.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorInsertion.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorInsertSpecial.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorMultiReplacement.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorNull.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorReplacement.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorReplaceTwo.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorSoftHyphens.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorSplitWord.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorSwap.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionGeneratorVowelChange.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionStatus.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionStrategy.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionStrategyOcr.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestion/SuggestionStrategyTyping.cpp \
	corevoikko/libvoikko/src/spellchecker/suggestions.cpp \
	corevoikko/libvoikko/src/spellchecker/VfstSpeller.cpp \
	corevoikko/libvoikko/src/spellchecker/VfstSuggestion.cpp \
	corevoikko/libvoikko/src/tokenizer/interface.cpp \
	corevoikko/libvoikko/src/tokenizer/Tokenizer.cpp \
	corevoikko/libvoikko/src/utils/StringUtils.cpp \
	corevoikko/libvoikko/src/utils/utils.cpp

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := libjnidispatch
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libjnidispatch.so

include $(PREBUILT_SHARED_LIBRARY)

