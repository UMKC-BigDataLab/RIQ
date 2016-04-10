from django.db import models


class indexdata(models.Model):

	BTC = 'BTC'
	LOGD = 'LOGD'
	D10 = 'D10'

	MY_CHOICESDATASET = (
		(BTC, 'BTC'),
		(LOGD, 'LOGD'),
		(D10,'D10-SML'),
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
	BLOOMCAPACITYFIRST = '2.5M'
	BLOOMCAPACITYSECOND = '5M'
	BLOOMCAPACITYTHIRD = '10M'


	MY_CHOICESBLOOMCAPACITY = (
		(BLOOMCAPACITYFIRST, '2.5M'),
		(BLOOMCAPACITYSECOND , '5M'),
		(BLOOMCAPACITYTHIRD, '10M'),
	)

	BLOOMERRORFIRST = 1
	BLOOMERRORSECOND = 5
	BLOOMERRORTHIRD = 10


	MY_CHOICESBLOOMERROR = (
		(BLOOMERRORFIRST, 1),
		(BLOOMERRORSECOND , 5),
		(BLOOMERRORTHIRD, 10),


	)

	Dataset = models.CharField( max_length=10, choices=MY_CHOICESDATASET,default=None )
	LHSKParameter = models.IntegerField(choices=MY_CHOICESLHSK,default=None )
	LHSLParameter = models.IntegerField( choices=MY_CHOICESLHSL,default=None )

	MaximumGraphs = models.IntegerField()
	BloomCapacity = models.CharField( max_length=10, choices=MY_CHOICESBLOOMCAPACITY,default=None )
	BloomError = models.IntegerField( choices=MY_CHOICESBLOOMERROR,default=None )
	IndexName = models.CharField(max_length=30)
	CreateDate = models.DateTimeField(max_length=20)
	TotalGraphs = models.IntegerField()
	GraphIndex = models.IntegerField()


class graphdata(models.Model):

	candidates = models.IntegerField(max_length=75)
	filtertime = models.DecimalField(max_digits=20, decimal_places=10)
	refinetime = models.DecimalField(max_digits=20, decimal_places=10)
	results = models.IntegerField(max_length=75)
	jena_candidates = models.IntegerField(max_length=75)
	jena_time = models.IntegerField(max_length=75)
	jena_results = models.IntegerField(max_length=75)
	jena_cold = models.DecimalField(max_digits=20, decimal_places=10)
	jena_warm = models.DecimalField(max_digits=20, decimal_places=10)

class querytable(models.Model):

	queryname = models.CharField(max_length=75)
	dataset = models.CharField(max_length=30)
	numberofresults = models.IntegerField(max_length=75)
	numberoftriples = models.IntegerField(max_length=75)
	cachesetting = models.CharField(max_length=75)

class queryfilenametable(models.Model):

	queryname = models.CharField(max_length=75)
	querydataset = models.CharField(max_length=75)
	queryfilename = models.CharField(max_length=75)


