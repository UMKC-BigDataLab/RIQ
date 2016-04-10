from django import forms
from django.forms import ModelForm
from indexinfo.models import indexdata
import datetime


class IndexConstructionForm(ModelForm):

	BTC = 'BTC'
	LOGD = 'LUBM'
	D10 = 'D10'

	MY_CHOICESDATASET = (
		(BTC, 'BTC (prebuilt)'),
		#(LOGD, 'Prebuilt LUBM'),
	#	(D10,'Upload ..'),
	)

	LHSKFIRST = 4
	LHSKSECOND = 8
	LHSKTHIRD = 16
	LHSKFOURTH = 30

	MY_CHOICESLHSK = (
		(LHSKFIRST, 4),
		(LHSKSECOND , 8),
		(LHSKTHIRD, 16),
		(LHSKFOURTH , 30),

	)

	LHSLFIRST = 4
	LHSLSECOND = 6
	LHSLTHIRD = 10
	LHSLFOURTH = 11

	MY_CHOICESLHSL = (
		(LHSLFIRST, 4),
		(LHSLSECOND , 6),
		(LHSLTHIRD, 10),
		(LHSLFOURTH , 11),

	)
	BLOOMCAPACITYZEROTH = '1000000'
	BLOOMCAPACITYFIRST  = '2500000'
	BLOOMCAPACITYSECOND = '5000000'
	BLOOMCAPACITYTHIRD  = '10000000'


	MY_CHOICESBLOOMCAPACITY = (
		(BLOOMCAPACITYZEROTH, '1M'),
		(BLOOMCAPACITYFIRST, '2.5M'),
		(BLOOMCAPACITYSECOND , '5M'),
		(BLOOMCAPACITYTHIRD, '10M'),
	)

	BLOOMERRORFIRST = 0.01
	BLOOMERRORSECOND = 0.05
	BLOOMERRORTHIRD = 0.1


	MY_CHOICESBLOOMERROR = (
		(BLOOMERRORFIRST, 1),
		(BLOOMERRORSECOND , 5),
		(BLOOMERRORTHIRD, 10),


	)

	GRAPHINDEXFIRST = 1000
	GRAPHINDEXSECOND = 100000
	GRAPHINDEXTHIRD = 1000000
	GRAPHINDEXFOURTH = 'all'

	MY_CHOICESGRAPHINDEX= (
		(GRAPHINDEXFIRST, '1,000'),
		(GRAPHINDEXSECOND , '100,000'),
		(GRAPHINDEXTHIRD, '1000,000'),
		(GRAPHINDEXFOURTH, 'ALL'),

	)
	dataset = forms.ChoiceField(choices=MY_CHOICESDATASET, widget = forms.Select)
	lhskparameter = forms.ChoiceField(choices=MY_CHOICESLHSK, widget = forms.Select)
	lhslparameter = forms.ChoiceField(choices=MY_CHOICESLHSL, widget = forms.Select)
	maximumgraphs = forms.TextInput(attrs={'class': 'form-control index','id':'maximumgraphs'}).render('value','')
	bloomcapacity = forms.ChoiceField(choices=MY_CHOICESBLOOMCAPACITY, widget = forms.Select)
	bloomerror = forms.ChoiceField(choices=MY_CHOICESBLOOMERROR, widget = forms.Select)
	graphindex = forms.ChoiceField(choices=MY_CHOICESGRAPHINDEX, widget = forms.Select)



	class Meta:
			model = indexdata
			exclude = ('Dataset','LHSKParameter','LHSLParameter','MaximumGraphs','BloomError', 'BloomCapacity', 'IndexName','CreateDate', 'TotalGraphs', 'GraphIndex')


