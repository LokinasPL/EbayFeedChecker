timestamps <- read.table("C:/Users/loubo/OneDrive/Documents/UNI/TELUQ/H24/INF1430/TimeLog-Final.txt", header=TRUE, stringsAsFactors=FALSE, numerals="no.loss", sep=",", na.strings="NA", dec=".", strip.white=TRUE)

library(Rmpfr)

timestamps$Time <- mpfr(timestamps$Time, base=10, precBits=64)

delaytable <- data.frame()
delaytable <- data.frame("Delay"=character(), "Type"=character())

for (i in 1:nrow(timestamps)){
 if (timestamps$Start.End[i]=="start"){
  for (j in i:nrow(timestamps)){
   if (timestamps$Start.End[j]=="end" && timestamps$Task[j]==timestamps$Task[i]){
    Delay <- timestamps$Time[j]-timestamps$Time[i]
    delaytable[nrow(delaytable)+1,] <- c(as(Delay, "character"),timestamps$Type[i])
    break
   }
  }
 }
}

delaytableog <- delaytable

delaytable$Delay <- mpfr(delaytable$Delay, base=10, precBits=64)

delayintra <- delaytable[delaytable$Type=="intra",]
delayinter <- delaytable[delaytable$Type=="inter",]

summary(delayintra$Delay)
summary(delayinter$Delay)

